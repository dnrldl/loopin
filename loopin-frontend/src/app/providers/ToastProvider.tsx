import React from 'react';
import { Toaster } from 'sonner';

const ToastProvider = ({ children }: { children: React.ReactNode }) => {
  return (
    <>
      {children}
      <Toaster
        position="bottom-right"
        richColors
        theme="system"
        toastOptions={{
          duration: 2500,
        }}
      />
    </>
  );
};

export default ToastProvider;
