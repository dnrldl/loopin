import { useEffect, useState } from 'react';

function useMediaQuery(query: string): boolean {
  const [matches, setMatches] = useState(() => {
    if (typeof window === 'undefined') return false;
    return window.matchMedia(query).matches;
  });

  useEffect(() => {
    const mediaQueryList = window.matchMedia(query);
    const listener = (event: MediaQueryListEvent) => setMatches(event.matches);

    mediaQueryList.addEventListener('change', listener);
    return () => mediaQueryList.removeEventListener('change', listener);
  }, [query]);

  return matches;
}

export function useDeviceSize() {
  const [isMounted, setIsMounted] = useState(false);

  useEffect(() => {
    setIsMounted(true);
  }, []);

  const isDesktop = useMediaQuery('(min-width: 1280px)');
  const isMobile = useMediaQuery('(max-width: 1023.99px)');

  if (!isMounted) {
    return {
      isDesktop,
      isMobile,
    };
  }

  return {
    isDesktop,
    isMobile,
  };
}
