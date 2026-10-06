# Use Node 20 LTS for the frontend build
FROM node:20-alpine AS builder
WORKDIR /app
COPY package*.json .
RUN npm ci
COPY . .
RUN npm run build

# Runtime image – use a lightweight server (nuxt start)
FROM node:20-alpine
WORKDIR /app
COPY --from=builder /app/.output .
EXPOSE 3000
CMD ["node",".output/server/index.mjs"]
