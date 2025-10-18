import { useEffect, useState } from 'react';

export default function Home() {
  const [health, setHealth] = useState(null);
  const [error, setError] = useState(null);

  useEffect(() => {
    const base = process.env.NEXT_PUBLIC_API_BASE_URL || 'http://localhost:8000';
    fetch(`${base}/health`)
      .then((r) => r.json())
      .then(setHealth)
      .catch((e) => setError(String(e)));
  }, []);

  return (
    <main style={{ fontFamily: 'sans-serif', padding: 24 }}>
      <h1>Frontend is running</h1>
      <p>
        API base URL: <code>{process.env.NEXT_PUBLIC_API_BASE_URL || 'http://localhost:8000'}</code>
      </p>
      <section style={{ marginTop: 16 }}>
        <h2>API /health</h2>
        {!health && !error && <p>Loading…</p>}
        {error && <pre style={{ color: 'crimson' }}>{error}</pre>}
        {health && <pre>{JSON.stringify(health, null, 2)}</pre>}
      </section>
    </main>
  );
}
