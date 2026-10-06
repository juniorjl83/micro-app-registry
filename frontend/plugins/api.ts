import { useRuntimeConfig } from '#app'
import axios from 'axios'

export const useApi = () => {
  const config = useRuntimeConfig()
  const baseUrl = config.public.apiBase

  const getToken = () => {
    // Prefer cookie, fallback to localStorage
    return useCookie('token').value || localStorage.getItem('token')
  }

  const instance = axios.create({
    baseURL: baseUrl,
    headers: {
      // Attach token if present
      ...(getToken() ? { Authorization: `Bearer ${getToken()}` } : {}),
    },
  })

  const get = async (path: string) => {
    const res = await instance.get(path)
    return res.data
  }

  const post = async (path: string, payload: any) => {
    const res = await instance.post(path, payload)
    return res.data
  }

  // Helper to store token after login
  const setToken = (token: string) => {
    useCookie('token').value = token
    localStorage.setItem('token', token)
    // Update axios header for subsequent requests
    instance.defaults.headers.common.Authorization = `Bearer ${token}`
  }

  const clearToken = () => {
    useCookie('token').value = null
    localStorage.removeItem('token')
    delete instance.defaults.headers.common.Authorization
  }

  return { get, post, setToken, clearToken }
}