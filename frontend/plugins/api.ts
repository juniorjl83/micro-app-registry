import { useRuntimeConfig } from '#app'
import axios from 'axios'

export const useApi = () => {
  const config = useRuntimeConfig()
  const baseUrl = config.public.apiBase

  const get = async (path: string) => {
    const res = await axios.get(`${baseUrl}${path}`)
    return res.data
  }

  const post = async (path: string, payload: any) => {
    const res = await axios.post(`${baseUrl}${path}`, payload)
    return res.data
  }

  return { get, post }
}
