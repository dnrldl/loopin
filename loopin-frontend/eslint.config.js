import js from '@eslint/js';
import pluginQuery from '@tanstack/eslint-plugin-query';
import { defineConfig, globalIgnores } from 'eslint/config';
import importPlugin from 'eslint-plugin-import';
import reactPlugin from 'eslint-plugin-react';
import reactHooks from 'eslint-plugin-react-hooks';
import reactRefresh from 'eslint-plugin-react-refresh';
import eslintPluginSimpleImportSort from 'eslint-plugin-simple-import-sort';
import globals from 'globals';
import tseslint from 'typescript-eslint';

export default defineConfig([
  globalIgnores(['dist']),
  {
    files: ['**/*.{ts,tsx,js}'],
    plugins: {
      react: reactPlugin,
      import: importPlugin,
      'simple-import-sort': eslintPluginSimpleImportSort,
      '@tanstack/query': pluginQuery,
    },
    extends: [
      js.configs.recommended, // js 기본 문법 오류, 코드 품질 검사
      tseslint.configs.recommended, // ts parser 활성화
      reactHooks.configs['recommended-latest'], // react hook 사용 위반 검사
      reactRefresh.configs.vite, // 핫 리로드 규칙 (vite 보조)
    ],
    languageOptions: {
      // window, document, fetch 등을 전역 객체로 인식
      ecmaVersion: 2020,
      globals: globals.browser,
    },

    rules: {
      // map 사용시 key 누락 방지
      'react/jsx-key': 'warn',

      // 미사용 변수 방지
      'no-unused-vars': 'off',
      '@typescript-eslint/no-unused-vars': 'warn',

      // react-query
      '@tanstack/query/exhaustive-deps': 'error',

      'simple-import-sort/imports': [
        'warn',
        {
          groups: [
            // React, 외부 라이브러리
            ['^react', '^@?\\w', '^@/'],
            // 내부 alias import (@/hooks, @/components 등)
            // ['^@/'],
            // 상대경로 (상위 → 동일 → 하위)
            ['^\\.\\.(?!/?$)', '^\\./(?=.*/)(?!/?$)', '^\\./?$'],
            // 스타일, CSS
            ['^.+\\.?(css)$'],
          ],
        },
      ],
      'simple-import-sort/exports': 'warn',
    },
  },
]);
