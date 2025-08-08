import { createApi } from '@reduxjs/toolkit/query/react';
import type { FieldValues } from 'react-hook-form';
import type { ApiResponse, LoginResponseType, UserType } from '../Types/Types';
import { baseQueryWith401Logout } from '../Types/baseQueryWith401Logout';

export const authApi = createApi({
  reducerPath: 'authApi',
  baseQuery: baseQueryWith401Logout,
  endpoints: (builder) => ({
    registerUser: builder.mutation<ApiResponse<UserType> ,FieldValues>({
      query: (userData : FieldValues) => ({
        url: 'auth/register',
        method: 'POST',
        body: userData,
      }),
    }),
    loginUser: builder.mutation<ApiResponse<LoginResponseType> , FieldValues>({
      query: (credentials : FieldValues) => ({
        url: 'auth/login',
        method: 'POST',
        body: credentials,
      }),
    }),
    getloginUserDetails: builder.mutation<ApiResponse<UserType> , string>({
      query: (token : String) => ({
        url: 'http://localhost:8080/user/loggedInUser',
        method: 'GET',
         headers: {
          Authorization: `Bearer ${token}`,
        },
      }),
    }),
    
    
  }),
});

export const {
  useRegisterUserMutation,
  useLoginUserMutation,
  useGetloginUserDetailsMutation
} = authApi;
