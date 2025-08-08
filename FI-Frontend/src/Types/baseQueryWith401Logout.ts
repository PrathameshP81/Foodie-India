// src/Api/baseQueryWith401Logout.ts
import {
  fetchBaseQuery,
  type BaseQueryFn,
  type FetchBaseQueryError,
} from '@reduxjs/toolkit/query/react';
import { loggedOutUser } from '../Reducers/AuthSlice';
import type { FetchArgs } from '@reduxjs/toolkit/query';

const baseQuery = fetchBaseQuery({
  baseUrl: 'http://localhost:8080/',
  prepareHeaders: (headers) => {
    const token = localStorage.getItem('authtoken');
    if (token) {
      headers.set('Authorization', `Bearer ${token}`);
    }
    return headers;
  },
});

export const baseQueryWith401Logout: BaseQueryFn<
  string | FetchArgs,
  unknown,
  FetchBaseQueryError
> = async (args, api, extraOptions) => {
  const result = await baseQuery(args, api, extraOptions);

  if (result.error && result.error.status === 401) {
    api.dispatch(loggedOutUser());
    localStorage.removeItem('authtoken');
  }

  return result;
};
