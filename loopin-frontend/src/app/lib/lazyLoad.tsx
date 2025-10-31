import { lazy, Suspense } from 'react';

export const lazyLoad = (
  factory: () => Promise<{ default: React.ComponentType }>,
) => {
  const Component = lazy(factory);
  return (
    <Suspense fallback={<div>Loading...</div>}>
      <Component />
    </Suspense>
  );
};
