<template>
  <div class="container mx-auto p-4">
    <h1 class="text-3xl font-bold mb-4">Micro‑App Marketplace</h1>
    <p class="mb-6">Discover modular SaaS tools you can try and subscribe to.</p>
    <div class="grid gap-4 md:grid-cols-2 lg:grid-cols-3">
      <CatalogCard v-for="app in apps" :key="app.id" :app="app" />
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useApi } from '@/composables/useApi'
import CatalogCard from '@/components/CatalogCard.vue'

interface MicroApp {
  id: string
  name: string
  shortDescription: string
  priceCents: number
  currency: string
  trialEnabled: boolean
}

const apps = ref<MicroApp[]>([])
const api = useApi()

onMounted(async () => {
  const data = await api.get('/micro-apps')
  apps.value = data as MicroApp[]
})
</script>

<style scoped>
.container { max-width: 1200px; }
</style>
