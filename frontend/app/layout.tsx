import './globals.css';
import type { ReactNode } from 'react';

export const metadata = {
  title: 'Monorepo Frontend',
  description: 'Next.js 14 + Tailwind starter',
};

export default function RootLayout({ children }: { children: ReactNode }) {
  return (
    <html lang="en">
      <body>{children}</body>
    </html>
  );
}
