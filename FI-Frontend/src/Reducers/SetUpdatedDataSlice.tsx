import { createSlice } from "@reduxjs/toolkit";
import type { UpdatedDataInitialStateType } from "../Types/Types";

const initialState: UpdatedDataInitialStateType = {
  updatedPlanData: {
    title: "",
    price: 0,
    category: "",
    description: "",
    recepies: [],
    planid: 0,
  },
  updatedPlanStatus: false,
};

export const setUpdatedDataSlice = createSlice({
  name: "setUpdatedDataSlice",
  initialState,
  reducers: {
    setUpdatedPlanData(state, action) {
      state.updatedPlanData.title = action.payload.title || "";
      state.updatedPlanData.category = action.payload.category || "";
      state.updatedPlanData.description = action.payload.description || "";
      state.updatedPlanData.price = action.payload.price || 0;
      state.updatedPlanData.recepies = action.payload.recepies || [];
      state.updatedPlanData.planid = action.payload.planid;
      state.updatedPlanStatus = true;
    },
    setUpdatedPlanStatus(state) {
      state.updatedPlanStatus = false;
      state.updatedPlanData.recepies = [];
    },
  },
});

export const { setUpdatedPlanData, setUpdatedPlanStatus } =
  setUpdatedDataSlice.actions;

export default setUpdatedDataSlice.reducer;
