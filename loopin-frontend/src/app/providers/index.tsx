import { RQProvider } from '@/app/providers/RQProvider';
import ToastProvider from '@/app/providers/ToastProvider';

export function AppProviders({ children }: { children: React.ReactNode }) {
  return (
    <RQProvider>
      <ToastProvider>{children}</ToastProvider>
    </RQProvider>
  );
}
