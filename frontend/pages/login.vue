<template>
  <div class="min-h-screen flex flex-col items-center justify-center bg-gray-50 p-4">
    <div class="w-full max-w-md space-y-6">
      <h2 class="text-2xl font-bold text-center">Sign in</h2>
      <form @submit.prevent="onSubmit" class="space-y-4">
        <div>
          <label class="block text-sm font-medium text-gray-700">Email</label>
          <input
            v-model="email"
            type="email"
            required
            class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
          />
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700">Password</label>
          <input
            v-model="password"
            type="password"
            required
            class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
          />
        </div>

        <div class="flex items-center justify-between">
          <div class="flex items-center">
            <input id="remember" type="checkbox" class="h-4 w-4 text-indigo-600 focus:ring-indigo-500" />
            <span class="ml-2 block text-sm text-left text-gray-900">
              Remember me
            </span>
          </div>
        </div>

        <button
          type="submit"
          disabled="submitting"
          class="w-full flex justify-center py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
        >
          {{ submitting ? 'Signing in…' : 'Sign in' }}
        </button>

        <p class="mt-3 text-center text-sm text-gray-500">
          Don’t have an account?
          <nuxt-link to="/register" class="font-medium text-indigo-600 hover:underline">
            Create one
          </nuxt-link>
        </p>
      </form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useAuth } from '@/composables/useAuth'
import { useRouter } from '#app'

const email = ref('')
const password = ref('')
const submitting = ref(false)
const router = useRouter()
const { login } = useAuth()

const onSubmit = async () => {
  submitting.value = true
  try {
    const res = await login(email.value, password.value)
    // Optionally redirect to a protected page after login
    await router.push('/')
  } catch (e: any) {
    alert(e.response?.data?.error ?? 'Login failed')
  } finally {
    submitting.value = false
  }
}
</script>