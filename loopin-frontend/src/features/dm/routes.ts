import { lazyLoad } from '@/app/lib/lazyLoad';

const DMLayout = lazyLoad(() => import('@/features/dm/layouts/DMLayout'));
const DMPage = lazyLoad(() => import('@/features/dm/pages/DMPage'));
const DMRoomPage = lazyLoad(() => import('@/features/dm/pages/DMRoomPage'));

export const dmRoutes = {
  path: 'dm',
  element: DMLayout,
  children: [
    {
      index: true,
      element: DMPage,
    },
    {
      path: ':chatId',
      element: DMRoomPage,
    },
  ],
};
