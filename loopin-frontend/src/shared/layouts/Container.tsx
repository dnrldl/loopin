import Header from './Header';
import Navbar from './Navbar';

interface ContainerProps {
  children: React.ReactNode;
}

const Container = ({ children }: ContainerProps) => {
  return (
    <div>
      <Header />
      {children}
      <Navbar />
    </div>
  );
};
Container.displayName = 'Container';

export default Container;
