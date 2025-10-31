import { useEffect } from 'react';
import { AppProviders } from '@/app/providers';
import AppRouter from '@/app/router/AppRouter';
import { useThemeStore } from '@/shared/stores/themeStore';

function App() {
  const theme = useThemeStore((s) => s.theme);
  const applyTheme = useThemeStore((s) => s.applyTheme);

  console.log(window.matchMedia('(prefers-color-scheme: dark)').matches);

  useEffect(() => {
    applyTheme(theme);
  }, [theme, applyTheme]);

  return (
    <AppProviders>
      <AppRouter />
    </AppProviders>
  );
}

export default App;
