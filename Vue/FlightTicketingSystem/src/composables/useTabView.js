import { computed } from "vue";
import CityTable from "@/components/travel/CityTable.vue";
import CityCardGrid from "@/components/travel/CityCardGridAdmin.vue";
import AttractionTable from "@/components/travel/AttractionTable.vue";
import AttractionCardGrid from "@/components/travel/AttractionCardGridAdmin.vue";

export function useTabView({
  viewMode,
  currentTab,
  cityStore,
  attractionStore,
  searchResults,
  handlers,
  headers,
}) {
  const getViewComponent = (tab) => {
    const isCard = viewMode.value === "Card";
    const map = {
      cities: isCard ? CityCardGrid : CityTable,
      allCities: isCard ? CityCardGrid : CityTable,
      attractions: isCard ? AttractionCardGrid : AttractionTable,
      allAttractions: isCard ? AttractionCardGrid : AttractionTable,
    };
    return map[tab];
  };

  const getViewProps = (tab) => {
    if (["cities"].includes(tab)) {
      return {
        cities: searchResults.value,
        headers: headers.city,
        handleEdit: handlers.edit,
        handleDelete: handlers.delete,
      };
    }
    if (["allCities"].includes(tab)) {
      return {
        cities: cityStore.cities,
        headers: headers.city,
        handleEdit: handlers.edit,
        handleDelete: handlers.delete,
      };
    }
    if (["attractions"].includes(tab)) {
      return {
        attractions: searchResults.value,
        cities: cityStore.cities,
        headers: headers.attraction,
        handleEdit: handlers.edit,
        handleDelete: handlers.delete,
      };
    }
    if (["allAttractions"].includes(tab)) {
      return {
        attractions: attractionStore.attractions,
        cities: cityStore.cities,
        headers: headers.attraction,
        handleEdit: handlers.edit,
        handleDelete: handlers.delete,
      };
    }
    return {};
  };

  return {
    getViewComponent,
    getViewProps,
  };
}
