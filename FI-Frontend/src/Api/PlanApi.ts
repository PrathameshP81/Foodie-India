import { createApi } from "@reduxjs/toolkit/query/react";
import type { ApiResponse, OrderResponse, PlanType } from "../Types/Types";
import { baseQueryWith401Logout } from "../Types/baseQueryWith401Logout";

export const planApi = createApi({
  reducerPath: "planApi",
  baseQuery: baseQueryWith401Logout,
  endpoints: (builder) => ({
    getAllPlans: builder.query<ApiResponse<PlanType[]>, void>({
      query: () => ({
        url: "plan",
        method: "GET",
      }),
    }),
    createPlan: builder.mutation<ApiResponse<PlanType>, FormData>({
      query: (planData) => ({
        url: "plan",
        method: "POST",
        body: planData,
      }),
    }),
    updatePlan: builder.mutation<ApiResponse<PlanType>, FormData>({
      query: (planData) => ({
        url: `plan/${Number(planData.get("planid"))}`,
        method: "PUT",
        body: planData,
      }),
    }),
    getPlanById: builder.query<ApiResponse<PlanType>, number>({
      query: (id: number) => ({
        url: `plan/${id}`,
        method: "GET",
      }),
    }),
    getPurchasedPlanByUser: builder.query<ApiResponse<PlanType[]>, void>({
      query: () => ({
        url: `plan/userPurchasedPlans`,
        method: "GET",
      }),
    }),
    setOrder: builder.mutation<OrderResponse, number>({
      query: (amount: number) => ({
        url: `auth/${amount}`,
        method: "POST",
      }),
    }),
    purchasePlan: builder.mutation<any, number>({
      query: (planid: number) => ({
        url: `plan/${planid}`,
        method: "POST",
      }),
    }),
  }),
});

export const {
  useCreatePlanMutation,
  useGetAllPlansQuery,
  useGetPlanByIdQuery,
  useGetPurchasedPlanByUserQuery,
  useSetOrderMutation,
  usePurchasePlanMutation,
  useUpdatePlanMutation,
} = planApi;
