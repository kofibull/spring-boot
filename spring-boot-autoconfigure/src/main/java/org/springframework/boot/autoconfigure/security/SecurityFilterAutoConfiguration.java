/*
 * Copyright 2012-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.boot.autoconfigure.security;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for Spring Security's Filter.
 * Configured separately from {@link SpringBootWebSecurityConfiguration} to ensure that
 * the filter's order is still configured when a user-provided
 * {@link WebSecurityConfiguration} exists.
 *
 * @author Rob Winch
 * @since 1.3
 */
@Configuration
@ConditionalOnWebApplication
@EnableConfigurationProperties
@AutoConfigureAfter(SpringBootWebSecurityConfiguration.class)
public class SecurityFilterAutoConfiguration {

	private static final String DEFAULT_FILTER_NAME = AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME;

	@Bean
	@ConditionalOnBean(name = DEFAULT_FILTER_NAME)
	public FilterRegistrationBean securityFilterChainRegistration(
			@Qualifier(DEFAULT_FILTER_NAME) Filter securityFilter,
			SecurityProperties securityProperties) {
		FilterRegistrationBean registration = new FilterRegistrationBean(securityFilter);
		registration.setOrder(securityProperties.getFilterOrder());
		registration.setName(DEFAULT_FILTER_NAME);
		return registration;
	}

}
