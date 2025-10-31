interface ContainerProps {
  left?: React.ReactNode;
  right?: React.ReactNode;
  children: React.ReactNode;
}

export function Container({ left, right, children }: ContainerProps) {
  return (
    <div className="flex min-h-screen justify-between">
      {/* 왼쪽 슬롯 */}
      {left && <aside className="w-64 bg-neutral-900">{left}</aside>}

      {/* 메인 콘텐츠 */}
      <main className="flex-1 bg-black text-white">{children}</main>

      {/* 오른쪽 슬롯 */}
      {right && <aside className="w-72 bg-neutral-800">{right}</aside>}
    </div>
  );
}
