module.exports = {
  devServer: {
      clientLogLevel: 'info',
      proxy: {
        '/api': {
          target: 'http://localhost:8080',
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
