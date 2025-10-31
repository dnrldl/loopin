import { create } from 'zustand';
import { persist } from 'zustand/middleware';

export type Theme = 'light' | 'dark' | 'system';

interface State {
  theme: Theme;
}

interface Action {
  setTheme: (theme: Theme) => void;
  toggleTheme: () => void;
  applyTheme: (theme?: Theme) => void;
}

export const useThemeStore = create<State & Action>()(
  persist(
    (set, get) => ({
      theme: 'system',

      // 테마 적용
      setTheme: (theme) => {
        set({ theme });
        get().applyTheme(theme);
      },

      // 테마 토글
      toggleTheme: () => {
        const current = get().theme === 'light' ? 'dark' : 'light';
        set({ theme: current });
        get().applyTheme(current);
      },

      // 테마 실제 적용
      applyTheme: (themeParam?: Theme) => {
        const theme = themeParam ?? get().theme;

        let resolved = theme;
        if (theme === 'system') {
          const prefersDark = window.matchMedia(
            '(prefers-color-scheme: dark)',
          ).matches;
          resolved = prefersDark ? 'dark' : 'light';
        }

        document.documentElement.dataset.theme = resolved;
      },
    }),
    {
      name: 'theme-storage',
      onRehydrateStorage: () => (state) => {
        if (state) state.applyTheme(state.theme);
      },
    },
  ),
);
