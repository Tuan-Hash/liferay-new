module.exports = {
  build: {
    output: "build/resources/main/META-INF/resources",
    bundler: {
      output: "build/resources/main/META-INF/resources",
      ignore: ['**/reactapp.js'],
      config: {
        imports: {
          'frontend-js-web': {
						'/': '*',
					},
        }
      }
    },
  },
};
