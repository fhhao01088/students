# Dev-friendly Next.js
FROM node:20-alpine

WORKDIR /app

# Leverage caching by copying only package.json first
COPY package.json /app/package.json

RUN npm install --no-audit --no-fund

# Copy the rest of the app
COPY . /app

EXPOSE 3000

# Use an anonymous volume for node_modules to avoid host/container permission conflicts during dev
VOLUME ["/app/node_modules"]

CMD ["npm", "run", "dev"]
