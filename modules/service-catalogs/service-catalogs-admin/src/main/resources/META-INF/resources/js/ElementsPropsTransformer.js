import { openConfirmModal } from 'frontend-js-web';

const ACTIONS = {
  delete(itemData) {
    openConfirmModal({
      message: Liferay.Language.get(
        'are-you-sure-you-want-to-delete-this'
      ),
      onConfirm: (isConfirmed) => {
        if (isConfirmed) {
          this.send(itemData.deleteURL);
        }
      },
    });
  },

  permissions(itemData) {
    Liferay.Util.openWindow({
      dialog: {
        destroyOnHide: true,
        modal: true,
      },
      dialogIframe: {
        bodyCssClass: 'dialog-with-footer',
      },
      title: Liferay.Language.get('permissions'),
      uri: itemData.permissionsURL,
    });
  },

  send(url) {
		submitForm(document.hrefFm, url);
	},
};

export default function propsTransformer({
  items,
  ...props
}) {
  return {
    ...props,
    items: items.map((item) => {
      return {
        ...item,
        onClick(event) {
          const action = item.data?.action;

          if (action) {
            event.preventDefault();

            ACTIONS[action](item.data);
          }
        },
      };
    }),
  };
}
