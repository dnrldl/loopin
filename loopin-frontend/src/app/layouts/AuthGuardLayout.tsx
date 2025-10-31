import { Navigate, Outlet } from 'react-router-dom';

const AuthGuardLayout = () => {
  const isLogin = true;

  if (!isLogin) return <Navigate to={'login'} replace />;

  return <Outlet />;
};

export default AuthGuardLayout;
