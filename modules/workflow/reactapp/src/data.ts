import { models, OrquestaModel } from './st2flow-model';
import { layout } from './st2flow-model/layout';
import MetaModel from './st2flow-model/model-meta';
import { debounce, difference, get, uniqueId } from 'lodash';

let workflowModel = new OrquestaModel();
const metaModel = new MetaModel();

metaModel.fromYAML(MetaModel.minimum);
metaModel.set('runner_type', 'orquesta');

function workflowModelGetter(model) {
  const { tasks, transitions, errors, input, vars } = model;

  const lastIndex = tasks
    .map(task => (task.name.match(/task(\d+)/) || [])[1])
    .reduce((acc, item) => Math.max(acc, item || 0), 0);

  model.on(
    'error',
    (err) => errors.push(...err)
  );

  if (model.checkWorkbook) {
    model.checkWorkbook();
  }

  model.validate();

  return {
    workflowSource: model.toYAML(),
    ranges: getRanges(model),
    tasks,
    input,
    vars,
    nextTask: `task${lastIndex + 1}`,
    transitions,
    notifications: errors.map(e => ({
      type: 'error',
      source: 'workflow',
      message: e.message,
      id: uniqueId(),
    })),
  };
}

function metaModelGetter(model) {
  const metaSource = model.toYAML();
  const { errors } = model;

  model.on(
    'error',
    (err) => errors.push(err[0].message)
  );

  return {
    metaSource,
    notifications: errors.map(e => ({ type: 'error', message: e.message, source: 'meta-yaml-error' })),
    meta: model.tokenSet.toObject(),
  };
}

function getRanges(model) {
  const ranges = {};

  model.tasks.forEach(task => {
    ranges[task.name] = workflowModel.getRangeForTask(task);
  });

  return ranges;
}

function extendedValidation(meta, model) {
  const errors = [];
  if (model.input) {
    const params = meta.parameters || {};
    const paramNames = Object.keys(params);
    const inputNames = model.input.map(input => {
      const key = typeof input === 'string' ? input : Object.keys(input)[0];
      return key;
    });
    paramNames.forEach(paramName => {
      if (!inputNames.includes(paramName)) {
        errors.push(`Parameter "${paramName}" must be in input`);
      }
    });
    model.input.forEach(input => {
      if (typeof input === 'string' && !params[input]) {
        errors.push(`Extra input "${input}" must have a value`);
      }
    });
  }

  return errors.length ? errors : null;
}

export const st2Data = (payload) => {
  const { workflowSource, metaSource } = payload;

  metaModel.applyDelta(null, metaSource);

  const runner_type = metaModel.get('runner_type');
  const Model = models[runner_type];

  if (workflowModel instanceof Model) {
    workflowModel.applyDelta(null, workflowSource);
  }
  else {
    workflowModel = new Model(workflowSource);
  }

  if (workflowModel.tasks.every(({ coords }) => !coords.x && !coords.y)) {
    layout(workflowModel);
  }

  return {
    // ...newState,
    metaSource,
    workflowSource,
    ...metaModelGetter(metaModel),
    ...workflowModelGetter(workflowModel),
    dirty: false,
  };
}