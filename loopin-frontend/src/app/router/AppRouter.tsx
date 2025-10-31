import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import RootLayout from '@/app/layouts/RootLayout';
import { dmRoutes } from '@/features/dm/routes';
import { postRoutes } from '@/features/post/routes';

const router = createBrowserRouter([
  {
    element: <RootLayout />,
    children: [postRoutes, dmRoutes],
  },
]);

const AppRouter = () => <RouterProvider router={router} />;

export default AppRouter;
