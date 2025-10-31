import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { ReactQueryDevtools } from '@tanstack/react-query-devtools';

const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      refetchOnWindowFocus: false, // 탭 포커스 시 재요청 방지
      // retry: 1, // 실패 시 1번만 재시도
      staleTime: 1000 * 60, // 1분 동안 데이터 신선
    },
  },
});

interface RQProviderProps {
  children: React.ReactNode;
}

export function RQProvider({ children }: RQProviderProps) {
  return (
    <QueryClientProvider client={queryClient}>
      {children}
      <ReactQueryDevtools />
    </QueryClientProvider>
  );
}
