import type { FilterInitialStateType } from "./Types";

export function capitalizeSentence(sentence: string) {
  return sentence
    .split(" ")
    .map((word) => word.charAt(0).toUpperCase() + word.slice(1).toLowerCase())
    .join(" ");
}
export function formatDate(dateStr: string): string {
  const date = new Date(dateStr);
  return date.toLocaleDateString("en-US", {
    month: "short",
    day: "numeric",
    year: "numeric",
  });
}

export function convertToMinutesAndSeconds(decimalMinutes: number) {
  const minutes = Math.floor(decimalMinutes);
  const seconds = Math.round((decimalMinutes - minutes) * 60);
  return `${minutes} m & ${seconds} s`;
}

interface FilterableItem {
  title: string;
  category: string;
  createdByUser: string | number;
  price?: number;
}

export const applyFilters = <T extends FilterableItem>(
  state: FilterInitialStateType,
  data: T[] = []
): T[] => {
  let result = [...data];

  const { searchQuery, category, createdBy, price } = state.filters;

  if (searchQuery.trim()) {
    result = result.filter((item) =>
      item.title.toLowerCase().includes(searchQuery.toLowerCase())
    );
  }

  if (category && category !== "All") {
    result = result.filter(
      (item) => item.category.toLowerCase() === category.toLowerCase()
    );
  }

  if (createdBy && createdBy !== "All") {
    result = result.filter(
      (item) => item.createdByUser.toString() === createdBy
    );
  }

  if (!isNaN(Number(price)) && Number(price) > 0) {
    result = result.filter((item) =>
      "price" in item ? Number(item.price ?? 0) <= Number(price) : true
    );
  }

  return result;
};
