import { createApi } from "@reduxjs/toolkit/query/react";
import type { ApiResponse, RecepieType } from "../Types/Types";
import { baseQueryWith401Logout } from "../Types/baseQueryWith401Logout";

export const recepieApi = createApi({
  reducerPath: "recepieApi",
  baseQuery: baseQueryWith401Logout,
  endpoints: (builder) => ({
    getAllRecepie: builder.query<ApiResponse<RecepieType[]>, void>({
      query: () => ({
        url: "recepie",
        method: "GET",
      }),
    }),
    getRecepieById: builder.query<ApiResponse<RecepieType>, number>({
      query: (id: number) => ({
        url: `recepie/${id}`,
        method: "GET",
      }),
    }),
    createRecepie: builder.mutation<ApiResponse<RecepieType>, FormData>({
      query: (recepieData) => ({
        url: "recepie",
        method: "POST",
        body: recepieData,
      }),
    }),
  }),
});

export const {
  useGetAllRecepieQuery,
  useCreateRecepieMutation,
  useGetRecepieByIdQuery,
} = recepieApi;
