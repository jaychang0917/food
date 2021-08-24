import 'package:base/base.dart';

import 'country_event.dart';
import 'country_state.dart';

class CountrySelectionBloc extends Bloc<CountrySelectionEvent, CountrySelectionState> {
  CountrySelectionBloc({
    required countryRepository,
  }) : _countryRepository = countryRepository, super(CountrySelectionState());

  final CountryRepository _countryRepository;

  @override
  Stream<CountrySelectionState> mapEventToState(CountrySelectionEvent event) async* {
    if (event is Initialized) {
      yield* _mapInitializedToState(event);
    } else if (event is SearchKeywordChanged) {
      yield* _mapSearchKeywordChangedToState(event);
    } else if (event is CountrySelected) {
      yield* _mapCountrySelectedToState(event);
    }
  }

  Stream<CountrySelectionState> _mapInitializedToState(Initialized event) async* {
    final selected = await _countryRepository.defaultCountry();
    final all = await _countryRepository.all();

    yield state.copyWith(
      allCountries: all,
      countryListState: _mapCountryList(all, selected),
      selectedCountryState: _mapCountryItem(selected, selected)
    );
  }

  List<CountryItemState> _mapCountryList(List<Country> items, Country selected) {
    return items.map((e) => _mapCountryItem(e, selected)).toList();
  }

  CountryItemState _mapCountryItem(Country country, Country selected) {
    return CountryItemState(
      country: country,
      flag: country.imageData,
      name: country.name,
      isSelected: country == selected
    );
  }

  Stream<CountrySelectionState> _mapSearchKeywordChangedToState(SearchKeywordChanged event) async* {
    final keyword = event.text;

    final matched = state.allCountries.where((element) =>
      element.name.toLowerCase().startsWith(keyword.toLowerCase())
    ).toList();
    final hasResult = matched.isNotEmpty;
    final isInSearchMode = keyword.isNotEmpty;

    final selectedCountry = state.selectedCountryState!.country;
    
    final updatedListState = isInSearchMode
      ? _mapCountryList(matched, selectedCountry)
      : _mapCountryList(state.allCountries, selectedCountry);
    yield state.copyWith(
      countryListState: updatedListState,
      isListSectionHeaderVisible: !isInSearchMode,
      isSelectedCountryHeaderVisible: !isInSearchMode,
      isEmptyResultVisible: !hasResult,
      searchKeyword: keyword
    );
  }

  Stream<CountrySelectionState> _mapCountrySelectedToState(CountrySelected event) async* {
    final country = event.country;
    _countryRepository.setDefaultCountry(country);
    
    yield state.copyWith(
      selectedCountryState: state.selectedCountryState!.copyWith(country: country),
      navigation: CloseNavigation()
    );
  }
}
