FROM yardenshoham/pagic AS builder

# Build website
COPY . .
RUN pagic build

FROM nginx:alpine AS runner

# Serve website on port 80
COPY --from=builder /pagic/dist /usr/share/nginx/html 