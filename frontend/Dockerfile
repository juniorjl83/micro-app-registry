# Use Node 20 LTS for the frontend
FROM node:20-alpine AS builder
WORKDIR /app
COPY package*.json .
RUN npm ci
COPY . .
RUN npm run build

# Runtime image – serves the generated dist with a lightweight server (e.g., nuxt start)
FROM node:20-alpine
WORKDIR /app
COPY --from=builder /app/.output .
EXPOSE 3000
CMD ["node",".output/server/index.mjs"]
