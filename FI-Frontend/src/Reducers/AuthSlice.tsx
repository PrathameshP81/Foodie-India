import { createSlice } from "@reduxjs/toolkit";
import type { AuthSliceInitalStateType } from "../Types/Types";

const initialState: AuthSliceInitalStateType = {
  token: localStorage.getItem("authtoken") || "",
  user: null,
  userId: null,
  isLoggedIn: false,
};

const AuthSlice = createSlice({
  name: "AuthSlice",
  initialState,
  reducers: {
    loggedInUser(state, action) {
      localStorage.setItem("authtoken", action.payload.token);
      state.token = action.payload.token;
      state.user = null;
      state.userId = action.payload.userId;
      state.isLoggedIn = true;
    },
    loggedOutUser(state) {
      localStorage.removeItem("authtoken");
      state.token = null;
      state.userId = null;
      state.user = null;
      state.isLoggedIn = false;
    },
    loggedInUserDetails(state, action) {
      state.userId = action.payload.userId;
      state.isLoggedIn = true;
      state.user = action.payload.user;
    },
  },
});

export const { loggedInUser, loggedOutUser, loggedInUserDetails } =
  AuthSlice.actions;

export default AuthSlice.reducer;
