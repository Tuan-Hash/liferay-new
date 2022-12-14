// Copyright 2021 The StackStorm Authors.
// Copyright 2020 Extreme Networks, Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

//@flow

import React, { Component } from 'react';
import { PropTypes } from 'prop-types';
import cx from 'classnames';

import style from './style.module.scss';

export default class CollapseButton extends Component {
  static propTypes = {
    state: PropTypes.bool,
    position: PropTypes.string,
    onClick: PropTypes.func.isRequired,
  }

  style = style

  handleClick(e: Event) {
    e.stopPropagation();
    this.props.onClick();
  }

  render() {
    const { position, state } = this.props;

    const { className, icon } = {
      left: {
        className: this.style.left,
        icon: state ? 'icon-chevron_right' : 'icon-chevron_left',
      },
      right: {
        className: this.style.right,
        icon: state ? 'icon-chevron_left' : 'icon-chevron_right',
      },
      top: {
        className: this.style.top,
        icon: state ? 'icon-chevron_down' : 'icon-chevron_up',
      },
    }[position] || {};

    return (
      <div className={cx(this.style['collapse-button'], className)} onClick={(e) => this.handleClick(e)}>
        <i className={icon} />
      </div>
    );
  }
}
