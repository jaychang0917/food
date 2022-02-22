import 'package:base/base.dart';

@immutable
class CountrySelectionState extends Equatable {
  CountrySelectionState({
    this.allCountries = const [],
    this.selectedCountryState,
    this.countryListState,
    this.isSelectedCountryHeaderVisible = true,
    this.isEmptyResultVisible = false,
    this.searchKeyword = '',
    this.isListSectionHeaderVisible = true,
    this.navigation
  });

  final List<Country> allCountries;

  final CountryItemState? selectedCountryState;

  final List<CountryItemState>? countryListState;

  final bool isSelectedCountryHeaderVisible;

  final bool isEmptyResultVisible;

  final String searchKeyword;

  final bool isListSectionHeaderVisible;

  final Navigation? navigation;

  CountrySelectionState copyWith({
    List<Country>? allCountries,
    CountryItemState? selectedCountryState,
    List<CountryItemState>? countryListState,
    bool? isSelectedCountryHeaderVisible,
    bool? isEmptyResultVisible,
    bool? isListSectionHeaderVisible,
    String? searchKeyword,
    Navigation? navigation
  }) {
    return CountrySelectionState(
      allCountries: allCountries ?? this.allCountries,
      selectedCountryState: selectedCountryState ?? this.selectedCountryState,
      countryListState: countryListState ?? this.countryListState,
      isSelectedCountryHeaderVisible: isSelectedCountryHeaderVisible ?? this.isSelectedCountryHeaderVisible,
      isEmptyResultVisible: isEmptyResultVisible ?? this.isEmptyResultVisible,
      searchKeyword: searchKeyword ?? this.searchKeyword,
      isListSectionHeaderVisible: isListSectionHeaderVisible ?? this.isListSectionHeaderVisible,
      navigation: navigation ?? this.navigation,
    );
  }

  @override
  List<Object?> get props => [
    allCountries,
    selectedCountryState,
    countryListState,
    isSelectedCountryHeaderVisible,
    isEmptyResultVisible,
    isListSectionHeaderVisible,
    searchKeyword
  ];
}

@immutable
class CountryItemState extends Equatable {
  const CountryItemState({
    required this.country,
    required this.flag,
    required this.name,
    required this.isSelected
  });

  final Country country;

  final String flag;

  final String name;

  final bool isSelected;

  CountryItemState copyWith({
    Country? country,
    String? flag,
    String? name,
    bool? isSelected
  }) {
    return CountryItemState(
      country: country ?? this.country,
      flag: flag ?? this.flag,
      name: name ?? this.name,
      isSelected: isSelected ?? this.isSelected,
    );
  }

  @override
  List<Object?> get props => [country, isSelected];
}

abstract class Navigation {}

class CloseNavigation extends Navigation {}
