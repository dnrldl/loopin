interface HeaderProps {
  left?: React.ReactNode;
  center?: React.ReactNode;
  right?: React.ReactNode;
}

const Header = ({ left, center, right }: HeaderProps) => {
  return (
    <header className="sticky top-0 flex justify-between">
      <div>left</div>
      <div>center</div>
      <div>right</div>
    </header>
  );
};

export default Header;
