import 'package:api/api.dart';

class CountryRepository {
  CountryRepository({required configApi}) : _configApi = configApi;

  final ConfigApi _configApi;

  var _defaultCountryId = "HK";

  Future<List<Country>> all() async {
    final response = await _configApi.getCountries();
    final data = response.data?.toList() ?? [];
    data.sort((Country a, Country b) => a.name.compareTo(b.name));
    return data;
  }

  Future<Country> defaultCountry() async {
    final countries = await all();
    return countries.firstWhere((element) => element.id == _defaultCountryId);
  }

  void setDefaultCountry(Country country) {
    _defaultCountryId = country.id;
  }
}
