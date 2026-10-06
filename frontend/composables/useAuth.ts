import useApi from '@/plugins/api'
import { useCookie } from '#app'

export const useAuth = () => {
  const api = useApi()
  const { setToken, clearToken } = api

  const login = async (email: string, password: string) => {
    const res = await api.post('/api/v1/auth/login', { email, password })
    setToken(res.accessToken)
    return res
  }

  const register = async (email: string, password: string, name: string) => {
    await api.post('/api/v1/auth/register', { email, password, name })
  }

  const logout = () => {
    clearToken()
  }

  return { login, register, logout }
}