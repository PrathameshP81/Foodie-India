export type UserType = {
  userId: number;
  name: string;
  email: string;
  password: string;
  gender: string;
  phone: string;
  role: string;
  purchasedPlanIds?: number[];
  createdPlanIds?: number[];
  createdRecepieIds?: number[];
  purchasedSubscriptionIds?: number[];
};

export type ApiResponse<T> = {
  message: string;
  data: T;
  status: number;
};
export type LoginResponseType = {
  userId: number;
  email: string;
  token: string;
};
// Auth Slice InitialState Type
export type AuthSliceInitalStateType = {
  userId: number | null;
  user: UserType | null;
  token: string | null;
  isLoggedIn: boolean;
};

//Plan Type
export type PlanType = {
  planid: number;
  title: string;
  price: number;
  category: string;
  description: string;
  thumbnaiUrl: string;
  createdAt: string;
  createdByUser: number;
  recepies?: null | any[];
  purchasedByUser?: number[];
  thumbnailPublicId?: string;
};

//Recepie Type
export type RecepieType = {
  id: number;
  title: string;
  category: "VEG" | "NON_VEG"; // Adjust based on your `Category` enum
  description: string;
  imageUrl: string;
  altText: string;
  width: number;
  height: number;
  ingrediants?: string;
  videoUrl: string;
  duration: string; // You can also use number if it's actually numeric
  imagePublicId?: string;
  videoPublicId?: string;
  createdByUser: number;
  planIds?: number[];
};

//Order Response
export type OrderResponse = {
  orderId: string | number;
  amount: number;
};

// Plan Filter type

export type FilterType = {
  searchQuery: string;
  category: string;
  createdBy: string;
  price: number;
};

// PlanInitialState

export type FilterInitialStateType = {
  filters: FilterType;
};

// Subscription Type
export type SubscriptionType = {
  subscription_id: number;
  subscription_name: string;
  subscription_description: string;
  subscription_price: number;
  durationInDays: number;
  createdAt: string;
  createdByUser: number;
};

// Purchase Subscription Type
export type SubscriptionPurchaseType = {
  id: number;
  user_id: number;
  subscription_id: number;
  startTime: string; // e.g., "2025-08-02T11:27:43.30675"
  endTime: string;
  purchasedAt: string;
  payment_id: string;
  order_id: string;
  active: boolean;
};

// UpdatedDataInitialStateType

type updatedPlanType = {
  title: string;
  price: number;
  planid: number;
  category: string;
  description: string;
  recepies: RecepieType[];
};
export type UpdatedDataInitialStateType = {
  updatedPlanData: updatedPlanType;
  updatedPlanStatus: boolean;
};
