import { createApi } from "@reduxjs/toolkit/query/react";
import type {
  ApiResponse,
  SubscriptionPurchaseType,
  SubscriptionType,
} from "../Types/Types";
import { baseQueryWith401Logout } from "../Types/baseQueryWith401Logout";

export const subscriptionApi = createApi({
  reducerPath: "subscriptionApi",
  baseQuery: baseQueryWith401Logout,
  endpoints: (builder) => ({
    getAllSubscription: builder.query<ApiResponse<SubscriptionType[]>, void>({
      query: () => ({
        url: "subscription",
        method: "GET",
      }),
    }),
    purchaseSubscription: builder.mutation<
      any,
      {
        subscriptionId: number;
        paymentDetails: {
          razorpay_payment_id: string;
          razorpay_order_id: string;
        };
      }
    >({
      query: ({ subscriptionId, paymentDetails }) => ({
        url: `subscription/${subscriptionId}`,
        method: "POST",
        body: paymentDetails,
      }),
    }),
    getPurchasedSubscription: builder.query<
      ApiResponse<SubscriptionPurchaseType[]>,
      void
    >({
      query: () => ({
        url: "subscription/user/purchasedSub",
        method: "GET",
      }),
    }),
  }),
});

export const {
  useGetAllSubscriptionQuery,
  usePurchaseSubscriptionMutation,
  useGetPurchasedSubscriptionQuery,
} = subscriptionApi;
