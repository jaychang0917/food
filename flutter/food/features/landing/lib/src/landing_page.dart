import 'dart:convert';

import 'package:base/base.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:landing/src/widgets/welcome_panel.dart';
import 'package:loginflow/loginflow.dart';

import 'landing_bloc.dart';
import 'landing_event.dart';
import 'landing_state.dart';

class LandingPage extends StatelessWidget {
  const LandingPage({Key? key}): super(key: key);

  @override
  Widget build(BuildContext context) {
    return BlocProvider(
      create: (context) {
        final countryRepository = RepositoryProvider.of<CountryRepository>(context);
        return LandingBloc(countryRepository: countryRepository)..add(Initialized());
      },
      child: BlocConsumer<LandingBloc, LandingState>(
        listener: (context, state) {
          if (state.navigation is LoginFlowNavigation) {
            Navigator.pushNamed(context, AppRoutes.loginFlow, arguments: LoginFlowData());
          }
        },
        builder: (context, state) {
          return Stack(
            children: [
              Container(color: FoodColors.accent),
              Align(
                alignment: Alignment.bottomLeft,
                child: WelcomePanel(
                  country: state.country,
                  onPhoneTextFieldTapped: () => BlocProvider.of<LandingBloc>(context).add(PhoneTextFieldTapped()),
                ),
              )
            ],
          );
        },
      ),
    );
  }
}