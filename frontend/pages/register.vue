<template>
  <div class="min-h-screen flex flex-col items-center justify-center bg-gray-50 p-4">
    <div class="w-full max-w-md space-y-6">
      <h2 class="text-2xl font-bold text-center">Create an account</h2>
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
            min-length="6"
            class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
          />
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700">Full name</label>
          <input
            v-model="name"
            type="text"
            required
            class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
          />
        </div>

        <div class="flex items-center justify-between">
          <div class="flex items-center">
            <input id="terms" type="checkbox" class="h-4 w-4 text-indigo-600 focus:ring-indigo-500" />
            <span class="ml-2 block text-sm text-gray-900">
              I agree to the <a href="#" class="text-indigo-600 hover:underline">Terms of Service</a>
            </span>
          </div>
        </div>

        <button
          type="submit"
          disabled="submitting"
          class="w-full flex justify-center py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
        >
          {{ submitting ? 'Creating…' : 'Create account' }}
        </button>

        <p class="mt-3 text-sm text-center text-gray-500">
          Already have an account?
          <nuxt-link to="/login" class="font-medium text-indigo-600 hover:underline">
            Sign in
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
const name = ref('')
const submitting = ref(false)
const router = useRouter()
const { register } = useAuth()

const onSubmit = async () => {
  submitting.value = true
  try {
    await register(email.value, password.value, name.value)
    // After registration, auto‑login or redirect to login page
    await router.push('/login')
  } catch (e: any) {
    alert(e.response?.data?.error ?? 'Registration failed')
  } finally {
    submitting.value = false
  }
}
</script>