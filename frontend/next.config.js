/** @type {import('next').NextConfig} */
const nextConfig = {
  reactStrictMode: true,
  // For local dev inside Docker behind a reverse proxy or custom host
  output: 'standalone'
};

module.exports = nextConfig;
