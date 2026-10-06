export default defineNuxtConfig({
  ssr: true,
  devtools: { enabled: true },
  modules: [
    '@pinia/nuxt',
    '@vueuse/nuxt',
  ],
  plugins: [
    '@/plugins/api.ts',
  ],
  runtimeConfig: {
    public: {
      apiBase: process.env.API_BASE || 'http://localhost:8080/api/v1',
    },
  },
  vite: {
    server: {
      // Proxy API calls to the backend container (Docker network name `backend`)
      proxy: {
        '/api': {
          target: 'http://backend:8080',
          changeOrigin: true,
          secure: false,
        },
      },
    },
  },
})
