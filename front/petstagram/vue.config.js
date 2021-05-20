module.exports = {
  devServer: {
      clientLogLevel: 'info',
      proxy: {
        '/api': {
          target: 'http://localhost:8000',
          changeOrigin: true,
          pathRewrite: {
            '^/api': ''
          }
        }
    }
  },
  transpileDependencies: [
      'vuetify'
  ]
}
