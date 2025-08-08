import { combineReducers, configureStore } from "@reduxjs/toolkit";
import { authApi } from "../Api/AuthApi";
import authReducer from "../Reducers/AuthSlice";
import updatedDataReducer from "../Reducers/SetUpdatedDataSlice";
import filterReducer from "../Reducers/FilterSlice";
import persistReducer from "redux-persist/es/persistReducer";
import { persistStore, type PersistConfig } from "redux-persist";
import storageSession from "redux-persist/lib/storage/session";
import { planApi } from "../Api/PlanApi";
import { userApi } from "../Api/UserApi";
import { recepieApi } from "../Api/RecepieApi";
import { subscriptionApi } from "../Api/SubscriptionApi";

const rootReducer = combineReducers({
  [authApi.reducerPath]: authApi.reducer,
  [planApi.reducerPath]: planApi.reducer,
  [userApi.reducerPath]: userApi.reducer,
  [recepieApi.reducerPath]: recepieApi.reducer,
  [subscriptionApi.reducerPath]: subscriptionApi.reducer,
  AuthSlice: authReducer,
  Filterslice: filterReducer,
  setUpdatedDataSlice: updatedDataReducer,
});

type RootReducerType = ReturnType<typeof rootReducer>;

const persistConfig: PersistConfig<RootReducerType> = {
  key: "root",
  storage: storageSession,
  whitelist: ["AuthSlice"],
};

// 👇 Wrap with persistReducer
const persistedReducer = persistReducer(persistConfig, rootReducer);

// 👇 Create the store
export const store = configureStore({
  reducer: persistedReducer,
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware({
      serializableCheck: false, // important for redux-persist
    }).concat(
      authApi.middleware,
      planApi.middleware,
      userApi.middleware,
      recepieApi.middleware,
      subscriptionApi.middleware
    ),
});

// 👇 Persistor for use in <PersistGate />
export const persistor = persistStore(store);

// 👇 RootState type
export type RootState = ReturnType<typeof store.getState>;
