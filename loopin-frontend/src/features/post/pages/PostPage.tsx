import { useThemeStore } from '@/shared/stores/themeStore';

const Div = () => <div className="">1</div>;

const PostPage = () => {
  const toggleTheme = useThemeStore((s) => s.toggleTheme);
  const theme = useThemeStore((s) => s.theme);

  return (
    <div className="bg-transparent text-[var(--color-text)]">
      <button
        className="cursor-pointer"
        onClick={() => {
          toggleTheme();
        }}
      >
        {theme === 'light' ? '다크모드' : '라이트모드'}
      </button>

      {<Div />}
      {<Div />}
      {<Div />}
      {<Div />}
    </div>
  );
};

export default PostPage;
