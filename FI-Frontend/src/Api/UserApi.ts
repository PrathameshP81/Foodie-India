import { createApi } from '@reduxjs/toolkit/query/react';
import type { ApiResponse,  UserType } from '../Types/Types';
import { baseQueryWith401Logout } from '../Types/baseQueryWith401Logout';

export const userApi = createApi({
  reducerPath: 'userApi',
  baseQuery: baseQueryWith401Logout,
  endpoints: (builder) => ({
    getAllUsers: builder.query<ApiResponse<UserType[]>, void>({
      query: () => ({
        url: 'user',
        method: 'GET',
      }),
    }),
  }),
});

export const {useGetAllUsersQuery} = userApi;
