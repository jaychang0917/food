import 'package:base/base.dart';
import 'package:flutter/material.dart';

import 'country_bloc.dart';
import 'country_event.dart';
import 'country_state.dart';
import 'widgets/CountryItem.dart';
import 'widgets/SectionIndex.dart';
import 'widgets/CountryList.dart';

class CountrySelectionPage extends StatelessWidget {
  CountrySelectionPage({Key? key}): super(key: key);

  final GlobalKey<CountryListState> _countryListKey = GlobalKey();

  @override
  Widget build(BuildContext context) {
    return BlocProvider(
      create: (context) {
        final countryRepository = RepositoryProvider.of<CountryRepository>(context);
        return CountrySelectionBloc(
          countryRepository: countryRepository
        )..add(Initialized());
      },
      child: BlocConsumer<CountrySelectionBloc, CountrySelectionState>(
        listener: (context, state) {
          if (state.navigation is CloseNavigation) {
            Navigator.pop(context, state.selectedCountryState!.country);
          }
        },
        builder: (context, state) => Stack(
          children: [
            Column(
              children: [
                FoodHeader.close(
                  title: context.strings.country_title,
                  onNavigationIconTapped: () => Navigator.pop(context)
                ),
                FoodTextField(
                  color: FoodColors.secondary,
                  placeholder: context.strings.country_search_hint,
                  autoFocus: false,
                  leadingIcon: Icon(Icons.search, color: FoodColors.primary),
                  onChanged: (text) => BlocProvider.of<CountrySelectionBloc>(context).add(SearchKeywordChanged(text)),
                ),
                if (state.isEmptyResultVisible)
                  Padding(
                    padding: const EdgeInsets.all(FoodSpacings.normal),
                    child: FoodText.body2(context.strings.country_search_empty_result(state.searchKeyword)),
                  ),
                if (state.selectedCountryState != null && state.isSelectedCountryHeaderVisible)
                  CountryItem(
                    item: state.selectedCountryState!,
                    isSelected: true,
                    onTapped: (item) => _onItemTapped(context, item)
                  ),
                if (state.countryListState != null && state.selectedCountryState != null)
                  Expanded(
                    child: CountryList(
                      key: _countryListKey,
                      items: state.countryListState!,
                      selected: state.selectedCountryState!,
                      isListSectionHeaderVisible: state.isListSectionHeaderVisible,
                      onItemSelected: (country) => _onItemTapped(context, country),
                    )
                  ),
              ],
            ),
            Align(
              alignment: Alignment.centerRight,
              child: SectionIndex(
                onLetterSelected: (header) => _countryListKey.currentState!.scrollToSection(header),
              )
            )
          ],
        ),
      ),
    );
  }

  void _onItemTapped(BuildContext context, Country country) {
    BlocProvider.of<CountrySelectionBloc>(context).add(CountrySelected(country));
  }
}