import { lazyLoad } from '@/app/lib/lazyLoad';

export const postRoutes = {
  index: true,
  element: lazyLoad(() => import('@/features/post/pages/PostPage')),
};
