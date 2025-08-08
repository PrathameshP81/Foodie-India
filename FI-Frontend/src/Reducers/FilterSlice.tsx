import { createSlice } from "@reduxjs/toolkit";
import type { FilterInitialStateType } from "../Types/Types";

const initialState: FilterInitialStateType = {
  filters: {
    searchQuery: "",
    category: "All",
    createdBy: "",
    price: 0,
  },
};

export const Filterslice = createSlice({
  name: "Filterslice",
  initialState,
  reducers: {
    applyFilters(state, action) {
      state.filters.searchQuery = action.payload.searchQuery;
      state.filters.category = action.payload.category;
      state.filters.createdBy = action.payload.createdBy;
      state.filters.price = action.payload.price;
    },
    resetFilters(state) {
      state.filters.searchQuery = "";
      state.filters.category = "All";
      state.filters.createdBy = "";
      state.filters.price = 0;
    },
  },
});

export const { applyFilters, resetFilters } = Filterslice.actions;

export default Filterslice.reducer;
