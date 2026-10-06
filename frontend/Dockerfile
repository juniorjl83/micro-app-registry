# Use Node 20 LTS for the frontend build
FROM node:20-alpine AS builder
WORKDIR /app

# Copy all source files
COPY . .

# Install dependencies (no lockfile present)
RUN npm install --omit=dev

# Copy the rest of the source code
COPY . .

# Build the production output
RUN npm run build

# Runtime image – use a lightweight server (nuxt start)
FROM node:20-alpine
WORKDIR /app
COPY --from=builder /app/.output .
EXPOSE 3000
CMD ["node","server/index.mjs"]
